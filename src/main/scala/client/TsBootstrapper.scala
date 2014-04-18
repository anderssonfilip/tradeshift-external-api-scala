package client

object TsBootstrapper {

  val BootstrapURL = "https://api.tradeshift.com/tradeshift/rest/"
  val SandboxBootstrapURL = "https://sandbox.tradeshift.com/tradeshift/rest/"
}

class TsBootstrapper(useSandBox: Boolean = false) {

  val base = if (useSandBox) TsBootstrapper.SandboxBootstrapURL else TsBootstrapper.BootstrapURL

}

