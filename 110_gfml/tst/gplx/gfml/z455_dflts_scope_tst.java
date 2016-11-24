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
package gplx.gfml; import gplx.*;
import org.junit.*;
public class z455_dflts_scope_tst {
	@Before public void setup() {
		regy = GfmlTypRegy.new_();
		GfmlTypeMakr makr = GfmlTypeMakr.new_();			
		rootPos = GfmlDocPos_.Root.NewDown(0);
		GfmlType type = makr.MakeRootType("point", "point", "x", "y").DocPos_(rootPos);
		regy.Add(type);
	}	GfmlDocPos rootPos, currPos; GfmlTypRegy regy;
	@Test  public void Basic() {
		currPos = rootPos.NewDown(0);
		tst_FetchOrNullByPos(regy, "point", rootPos, "point", "x", "y");
		tst_FetchOrNullByPos(regy, "point", currPos, "point", "x", "y");

		List_adp list = List_adp_.New();
		list.Add(GfmlDefaultItem.new_("point", "z", GfmlTkn_.raw_("0")));
		GfmlDefaultPragma_bgnCmd.ExecList(regy, currPos, list);

		tst_FetchOrNullByPos(regy, "point", rootPos, "point", "x", "y");
		tst_FetchOrNullByPos(regy, "point", currPos, "point", "x", "y", "z");
	}
	GfmlType tst_FetchOrNullByPos(GfmlTypRegy regy, String key, GfmlDocPos docPos, String expdTypeKey, String... expdSubs) {
		GfmlType actl = regy.FetchOrNull(key, docPos);
		Tfds.Eq(expdTypeKey, actl.Key());
		String[] actlSubs = new String[actl.SubFlds().Count()];
		for (int i = 0; i < actlSubs.length; i++)
			actlSubs[i] = actl.SubFlds().Get_at(i).Name();
		Tfds.Eq_ary(expdSubs, actlSubs);
		return actl;
	}
}
