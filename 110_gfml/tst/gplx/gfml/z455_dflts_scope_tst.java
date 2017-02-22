/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
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
