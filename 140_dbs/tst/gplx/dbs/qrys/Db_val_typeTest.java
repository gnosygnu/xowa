package gplx.dbs.qrys;

import gplx.Byte_;
import gplx.DateAdp_;
import gplx.Decimal_adp_;
import gplx.core.tests.Gftest;
import gplx.objects.lists.GfoListBase;
import org.junit.Test;

public class Db_val_typeTest {
	@Test public void Numbers() {
		TestToSqlStr
		("ABC true 1 2 3 4.0 5 6 XYZ"
		,"ABC ? ? ? ? ? ? ? XYZ"
		, true, Byte_.By_int(1), 2, (long)3, (float)4, (double)5, Decimal_adp_.int_(6)
		);
	}
	@Test public void Strings() {
		TestToSqlStr
		("ABC 'abc' 'a\\'\"c' 'xyz' 'x\\'\"z' '2021-01-02 03:04:05.006' XYZ"
		,"ABC ? ? ? ? ? XYZ"
		, "abc", "a'\"c", "xyz", "x'\"z", DateAdp_.new_(2021, 1, 2, 3, 4, 5, 6)
		);
	}
	private void TestToSqlStr(String expd, String sql, Object... paramArray) {
		String actl = Db_val_type.ToSqlStr(sql, new GfoListBase<>().AddMany(paramArray));
		Gftest.Eq__str(expd, actl);
	}
}
