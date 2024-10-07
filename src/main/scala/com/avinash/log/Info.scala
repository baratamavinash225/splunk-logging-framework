package com.avinash.log

sealed trait Info

object UserInfo extends Info {
  val name: String = System.getProperty("user.name")
}

object MachineInfo extends Info {
  val hostname: String = java.net.InetAddress.getLocalHost.getHostName
}