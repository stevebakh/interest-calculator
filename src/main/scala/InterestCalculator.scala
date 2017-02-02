import scala.math.BigDecimal.RoundingMode

object InterestCalculator {
  private val DefaultRates = Seq(
    InterestRate(0.00, 999.99, 0.01),
    InterestRate(1000.00, 4999.99, 0.02),
    InterestRate(5000.00, Double.MaxValue, 0.03))

  def apply(amount: BigDecimal): BigDecimal = apply(amount, DefaultRates)

  def apply(amount: BigDecimal, rates: Seq[InterestRate]): BigDecimal =
    rates
      .find(r => r.lower <= amount && r.upper >= amount)
      .map(_.rate * amount)
      .getOrElse(BigDecimal(0))
      .setScale(2, RoundingMode.DOWN)
}
