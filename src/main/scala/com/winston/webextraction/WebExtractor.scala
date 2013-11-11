package com.winston.webextraction

import com.winston.utlities.Tools

class WebExtractor {
	val diffbotBase:String = "http://www.diffbot.com/api/article?token=2a418fe6ffbba74cd24d03a0b2825ea5&url="
	var diffbotUrl:String = null
	var response:JsonNode = null
	
	def this(url:String){
	  this()
	  diffbotUrl = constructUrl(url)
	  response = Tools.fetchURL(diffbotUrl)
	}
	
	def getText():String = {
	  try{
	    var text = response.get("text").asText()
	    text = lintBody(text)
	    
	    return text
	    
	  } catch{
	    case e:Exception =>{
	      e.printStackTrace()
	      return null
	    }
	  }
	}
	
	def getHeadline():String = {
	  try{
	    
	    var headline = response.get("text").asText()
	    return headline
	    
	  } catch {
	    case e:Exception =>{
	      e.printStackTrace()
	      return null
	    }
	  }
	}
	
	def constructUrl(url:String):String = {
	  return diffbotBase + url
	}
	
	def lintBody(text:String):String = {
	  var paragraphs = text.split("[\\r\\n]")
	  
	  var newParagraphs = ""
	    
	  for(paragraph <- paragraphs){
	    var testString = paragraph.replaceAll("\\W", "NONWORD")
	    
	    if(testString.endsWith("NONWORD")){
	    	if(paragraph.endsWith(":") || paragraph.endsWith(")")) {
	    	  if(paragraph.length() >= 85){
	    		 System.out.println("plen : " + paragraph.length())
	    		 newParagraphs += paragraph + "\n"
	    	  }
	    	}
	    	else{
	    	  newParagraphs += paragraph + "\n"
	    	}
	    }  
	  }
	  return newParagraphs
	}
}