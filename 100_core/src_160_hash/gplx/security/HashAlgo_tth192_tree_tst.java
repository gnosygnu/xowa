/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.security; import gplx.*;
import org.junit.*;
public class HashAlgo_tth192_tree_tst {
	@Test  public void CalcRecursiveHalves() {
		tst_CalcRecursiveHalves(129, 128);
		tst_CalcRecursiveHalves(128, 127);
		tst_CalcRecursiveHalves(100, 99);
		tst_CalcRecursiveHalves(20, 19);
		tst_CalcRecursiveHalves(6, 5);
		tst_CalcRecursiveHalves(5, 4);
		tst_CalcRecursiveHalves(4, 3);
		tst_CalcRecursiveHalves(3, 2);
		tst_CalcRecursiveHalves(2, 1);
		tst_CalcRecursiveHalves(1, 0);
		tst_CalcRecursiveHalves(0, 0);
	}
	@Test  public void CalcWorkUnits() {
		tst_CalcWorkUnits(101, 21); // leafs; 10 full, 1 part (+11) -> reduce 11 to 5+1 (+5) -> reduce 6 to 3 (+3) -> reduce 3 to 1+1 (+1) -> reduce 2 to 1 (+1)
		tst_CalcWorkUnits(100, 19); // leafs; 10 full (+10) -> reduce 10 to 5 (+5) -> reduce 5 to 2+1 (+2) -> reduce 3 to 1+1 (+1) -> reduce 2 to 1 (+1)
		tst_CalcWorkUnits(30, 5); // leafs; 3 full (+3) -> reduce 3 to 1+1 (+1) -> reduce 2 to 1 (+1)
		tst_CalcWorkUnits(11, 3); // leafs: 1 full, 1 part (+2) -> reduce 2 to 1 (+1)
		tst_CalcWorkUnits(10, 1);
		tst_CalcWorkUnits(9, 1);
		tst_CalcWorkUnits(1, 1);
		tst_CalcWorkUnits(0, 1);
	}
	void tst_CalcWorkUnits(int length, int expd) {
		HashAlgo_tth192 algo = HashAlgo_tth192.new_(); algo.BlockSize_set(10);
		int actl = algo.CalcWorkUnits(length);
		Tfds.Eq(expd, actl);
	}
	void tst_CalcRecursiveHalves(int val, int expd) {
		int actl = CalcRecursiveHalvesMock(val);
		Tfds.Eq(expd, actl);
	}
	int CalcRecursiveHalvesMock(int val) {
		if (val <= 1) return 0;
		int rv = 0;
		while (true) {
			int multiple = val / 2;
			int remainder = val % 2;
			rv += multiple;
			val = multiple + remainder;
			if (val == 1) 
				return remainder == 0 ? rv : ++rv;
		}
	}
}
