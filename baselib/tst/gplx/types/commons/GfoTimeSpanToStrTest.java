package gplx.types.commons;
import gplx.frameworks.tests.GfoTstr;
import org.junit.Test;
public class GfoTimeSpanToStrTest {
	@Test public void Zero() {
		TestDefault(0, "0");
	}
	@Test public void MinuteSeconds() {
		TestDefault(77000, "1:17");
	}
	@Test public void ZeroSuppression() {
		TestDefault(660000, "11:00");    //fractional 0 and leading 0s are suppressed; i.e.: not 00:11:00.000
	}
	@Test public void HourTest() {
		TestDefault(3723987, "1:02:03.987");
	}
	@Test public void NegSeconds() {
		TestDefault(-2000, "-2");
	}
	@Test public void NegMins() {
		TestDefault(-60000, "-1:00");
	}
	@Test public void NegHours() {
		TestDefault(-3723981, "-1:02:03.981");
	}
	@Test public void ZeroPadding() {
		TestZeroPadding("0", "00:00:00.000");
		TestZeroPadding("1:02:03.123", "01:02:03.123");
		TestZeroPadding("1", "00:00:01.000");
		TestZeroPadding(".987", "00:00:00.987");
		TestZeroPadding("2:01.456", "00:02:01.456");
	}
	private void TestDefault(long fractionals, String expd) {
		GfoTimeSpan ts = GfoTimeSpanUtl.NewFracs(fractionals);
		String actl = ts.ToStr(GfoTimeSpanUtl.Fmt_Default);
		GfoTstr.Eq(expd, actl);
	}
	private void TestZeroPadding(String val, String expd) {
		GfoTimeSpan timeSpan = GfoTimeSpanUtl.Parse(val);
		String actl = timeSpan.ToStr(GfoTimeSpanUtl.Fmt_PadZeros);
		GfoTstr.Eq(expd, actl);
	}
}
