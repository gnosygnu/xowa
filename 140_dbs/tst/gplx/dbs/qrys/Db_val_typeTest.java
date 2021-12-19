package gplx.dbs.qrys;

import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.commons.GfoDateUtl;
import gplx.types.commons.GfoDecimalUtl;
import gplx.types.commons.lists.GfoListBase;
import org.junit.Test;

public class Db_val_typeTest {
	@Test public void Numbers() {
		TestToSqlStr
		("ABC true 1 2 3 4.0 5 6 XYZ"
		,"ABC ? ? ? ? ? ? ? XYZ"
		, true, ByteUtl.ByInt(1), 2, (long)3, (float)4, (double)5, GfoDecimalUtl.NewByInt(6)
		);
	}
	@Test public void Strings() {
		TestToSqlStr
		("ABC 'abc' 'a\\'\"c' 'xyz' 'x\\'\"z' '2021-01-02 03:04:05.006' XYZ"
		,"ABC ? ? ? ? ? XYZ"
		, "abc", "a'\"c", "xyz", "x'\"z", GfoDateUtl.New(2021, 1, 2, 3, 4, 5, 6)
		);
	}
	private void TestToSqlStr(String expd, String sql, Object... paramArray) {
		String actl = Db_val_type.ToSqlStr(sql, new GfoListBase<>().AddMany(paramArray));
		GfoTstr.Eq(expd, actl);
	}
}
