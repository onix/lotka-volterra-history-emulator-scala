package name.onix.emulators.lotkavolterrahistory

import computer.LotkaVolterraEquationSolver

class LotkaVolterraWebApp extends LotkavolterraHistoryEmulatorStack {

  get("/") {
    contentType = "text/html"
    val path = "/WEB-INF/index.html"

    new java.io.File(getServletContext.getResource(path).getFile)
  }

  get("/js/:jsfile") {
    contentType = "text/javascript"
    val path = "/WEB-INF/js/" + params("jsfile")
    new java.io.File(getServletContext.getResource(path).getFile)
  }

  get("/css/:cssfile") {
    contentType = "text/css"
    val path = "/WEB-INF/css/" + params("cssfile")
    new java.io.File(getServletContext.getResource(path).getFile)
  }

  post("/compute/:a/:b/:c/:d/:t0/:t1/:dt/:predatorsInitial/:preyInitial") {
    def returnArrayString(input: List[List[Double]]) :String = {
      var predatorsOutputJsArrayStringBuilder: StringBuilder = new StringBuilder
      var preyOutputJsArrayStringBuilder: StringBuilder = new StringBuilder

      for (obj <- input(0)) {
        predatorsOutputJsArrayStringBuilder.append(obj.toString).append(", ")
      }

      for (obj <- input(1)) {
        preyOutputJsArrayStringBuilder.append(obj.toString + ", ")
      }

      "{\"predators\": [" + predatorsOutputJsArrayStringBuilder.toString().dropRight(2) + "]," +
        "\"prey\": [" + preyOutputJsArrayStringBuilder.toString().dropRight(2) + "]}"
    }

    try {
      val alpha = params("a").toDouble
      val beta = params("b").toDouble
      val gamma = params("c").toDouble
      val delta = params("d").toDouble
      val dt = params("dt").toDouble

      val iterationsAmount = ((params("t1").toDouble - params("t0").toDouble) / params("dt").toDouble).toInt
      val predatorsInitialAmount = params("predatorsInitial").toInt
      val preyInitialAmount = params("preyInitial").toInt

      val computeResult = new LotkaVolterraEquationSolver
      contentType = "application/json"
      val res = computeResult.lotkaVolterraCompute(alpha, beta, gamma, delta, dt,
        iterationsAmount, predatorsInitialAmount, preyInitialAmount)
      returnArrayString(res)
    } catch { case _: Throwable => None }
  }
  
}
