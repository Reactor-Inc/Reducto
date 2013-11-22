package com.winston.nlp.bootstrap

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory
import akka.kernel.Bootable
import com.reactor.nlp.utilities.IPTools
import com.reactor.nlp.config.SystemCreator

class FrontendDaemon(args:Array[String]) extends Bootable {
	val ip = IPTools.getPrivateIp();

	val config =
      (if (args.nonEmpty) ConfigFactory.parseString(s"akka.remote.netty.tcp.port=${args(0)}") else ConfigFactory.empty)
      .withFallback(ConfigFactory.parseString("akka.cluster.roles = [reducto-frontend]\nakka.remote.netty.tcp.hostname=\""+ip+"\"")).withFallback(ConfigFactory.load("reducto"))
      
    val system = ActorSystem("NLPClusterSystem-0-1", config)
    
	def startup(){
	}

	def shutdown(){
		system.shutdown()
	}
}

object FrontendDaemon {
	def main(args:Array[String]){
		var bootstrap = new FrontendDaemon(args)
		bootstrap.startup()
		println("Frontend node running...")
	}
}