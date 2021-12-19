package gplx.apis.types;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ArrayLni;
import org.junit.Test;
public class ArrayLniTest {
	private final ArrayApiTstr tstr = new ArrayApiTstr();
	@Test public void ResizeAddAry() {
		tstr.TestResizeAddAry("basic"    , new String[] {"a", "b", "c"}, new String[] {"d", "e"}, new String[] {"a", "b", "c", "d", "e"});
		tstr.TestResizeAddAry("add.empty", new String[] {"a", "b", "c"}, new String[0], new String[] {"a", "b", "c"});
		tstr.TestResizeAddAry("add.null" , new String[] {"a", "b", "c"}, null, new String[] {"a", "b", "c"});
	}
}
class ArrayApiTstr {
	public void TestResizeAddAry(String note, String[] src, String[] add, String[] expd) {GfoTstr.EqLines(expd, (String[])ArrayLni.ResizeAddAry(src, add), note);}
}