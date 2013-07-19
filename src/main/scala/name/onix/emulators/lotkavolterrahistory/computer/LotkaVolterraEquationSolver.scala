package name.onix.emulators.lotkavolterrahistory.computer

/**
 * This class implements Lotka-Volterra equation solver.
 *
 * lotkaVolterraCompute() is a base function that returns a structure
 * List(ListOfPredators[Double], ListOfPrey[Double]), which contains
 * a List of population history for predators and a List of population
 * history for prey during the period of iterationsAmount parameter.
 *
 * main() function contains a small demo
 *
 * Feel free to use and distribute.
 */
class LotkaVolterraEquationSolver {
  /**
   * Lotka-Volterra equation coefficients and initial values:
   * @param alpha Intrinsic rate of prey population increase
   * @param beta Predation rate coefficient
   * @param gamma Predator mortality rate
   * @param delta Reproduction rate of predators per 1 prey eaten
   *
   * @param dt Delta of time
   * @param iterationsAmount Number of iterations to simulate
   * @param predatorsAmount Initial density of predators
   * @param preyAmount Initial density of prey
   */
  def lotkaVolterraCompute(alpha: Double, beta: Double, gamma: Double, delta: Double,
                           dt: Double, iterationsAmount: Int, predatorsAmount: Int, preyAmount: Int): List[List[Double]] = {

    /**
     * Calculates the change in prey population size using the Lotka-Volterra equation for prey
     *
     * @param x Amount of prey
     * @param y Amount of predators
     * @return Prey population change
     */
    def dx(x: Double, y: Double): Double = {
      val dx_dt = x * (alpha - beta * y) // Calculate the rate of population change
      dx_dt * dt
    }

    /**
     * Calculates the change in predator population size using the Lotka-Volterra equation for predators
     *
     * @param x Amount of prey
     * @param y Amount of predators
     * @return Predator population change
     */
    def dy(x: Double, y: Double): Double = {
      val dy_dt = y * (delta * x - gamma) // Calculate the rate of population change
      dy_dt * dt
    }

    /**
     * Calculates the predator/prey population growth for the given parameters.
     *
     * @return List of two lists of Doubles -- first is for predator
     *         population history, second is for prey population history
     */
    def calculate(currentIteration: Int, currentPredatorsAmount: Double, currentPreyAmount: Double,
                  predatorsList: List[Double], preyList: List[Double]): List[List[Double]] = {

      if (currentIteration < iterationsAmount) {
        val xk_1 = dx(currentPreyAmount, currentPredatorsAmount)
        val yk_1 = dy(currentPreyAmount, currentPredatorsAmount)
        val xk_2 = dx(currentPreyAmount + xk_1, currentPredatorsAmount + yk_1)
        val yk_2 = dy(currentPreyAmount + xk_1, currentPredatorsAmount + yk_1)

        val newPredatorsAmount = currentPredatorsAmount + (yk_1 + yk_2) / 2
        val newPreyAmount = currentPreyAmount + (xk_1 + xk_2) / 2

        val newPredatorsList = newPredatorsAmount :: predatorsList
        val newPreyList = newPreyAmount :: preyList

        calculate(currentIteration + 1, newPredatorsAmount, newPreyAmount, newPredatorsList, newPreyList)
      } else
        List(predatorsList.reverse, preyList.reverse)
    }
    calculate(0, predatorsAmount, preyAmount, List(), List())
  }

  def main(args: Array[String]) {
    println(lotkaVolterraCompute(1.0, 0.1, 1.0, 0.075, 0.02, 1000, 5, 10))
  }
}
