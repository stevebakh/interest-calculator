import org.scalatest.FunSpec
import org.scalatest.Matchers

class InterestCalculatorTest extends FunSpec with Matchers {
  describe("Calculate interest amount for account balances") {
    it("amount returned for a balance of 0.00 should be 0.00") {
      InterestCalculator(BigDecimal(0.00)) should be(BigDecimal(0.00))
    }

    it("amount returned for a negative balance should be 0.00") {
      InterestCalculator(BigDecimal(-100.00)) should be(BigDecimal(0.00))
      InterestCalculator(BigDecimal(-0.01)) should be(BigDecimal(0.00))
    }

    it("should calculate 1% for an account balance from 0 up to and including £999.99") {
      InterestCalculator(BigDecimal(999.99)) should be(BigDecimal(9.99))
      InterestCalculator(BigDecimal(0.01)) should be(BigDecimal(0.00))
      InterestCalculator(BigDecimal(100.00)) should be(BigDecimal(1.00))
    }

    it("should calculate 2% for an account balance from £1000 up to and including £4999.99") {
      InterestCalculator(BigDecimal(1000.00)) should be(BigDecimal(20.00))
      InterestCalculator(BigDecimal(4999.99)) should be(BigDecimal(99.99))
    }

    it("should calculate 3% for an account balance from £5000 and above") {
      InterestCalculator(BigDecimal(5000.00)) should be(BigDecimal(150.00))
      InterestCalculator(BigDecimal(100000.00)) should be(BigDecimal(3000.00))
    }
  }

  describe("Calculate interest amount for account balances using a custom set of rates") {
    val rates = Seq(
      InterestRate(0.00, 1999.99, 0.02),
      InterestRate(2000.00, 9999.99, 0.03),
      InterestRate(10000.00, Double.MaxValue, 0.04))

    it("amount returned for a balance of 0.00 should be 0.00") {
      InterestCalculator(BigDecimal(0.00)) should be(BigDecimal(0.00))
    }

    it("amount returned for a negative balance should be 0.00") {
      InterestCalculator(BigDecimal(-100.00)) should be(BigDecimal(0.00))
      InterestCalculator(BigDecimal(-0.01)) should be(BigDecimal(0.00))
    }

    it("should calculate 2% for an account balance from 0 up to and including £1999.99") {
      InterestCalculator(BigDecimal(1999.99), rates) should be(BigDecimal(39.99))
      InterestCalculator(BigDecimal(100.00), rates) should be(BigDecimal(2.00))
    }
  }
}
