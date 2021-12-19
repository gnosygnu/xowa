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
package gplx.gfui.imgs;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoIoTstr;
import gplx.types.commons.lists.CompareAbleUtl;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.commons.GfoDate;
import gplx.libs.files.Io_url;
import org.junit.*;
import gplx.core.security.algos.*;
public class ImageAdp_tst {
	@Before public void setup() {
		load = GfoIoTstr.RscDir.GenSubFil_nest("150_gfui", "imgs", "strawberry_java.bmp");
	}	ImageAdp img; Io_url load;
	@Test public void load_() {
		img = ImageAdp_.file_(load);
		GfoTstr.EqObj(80, img.Width());
		GfoTstr.EqObj(80, img.Height());
		GfoTstr.EqObj("80,80", img.Size().toString());
		GfoTstr.EqObj(img.Url(), load);
	}
	@Test public void SaveAsBmp() {
		img = ImageAdp_.file_(load);
		Io_url save = load.GenNewNameOnly("strawberry_temp");
		GfoDate beforeModifiedTime = Io_mgr.Instance.QueryFil(save).ModifiedTime();
		img.SaveAsBmp(save);
		GfoDate afterModifiedTime = Io_mgr.Instance.QueryFil(save).ModifiedTime();
		GfoTstr.EqBoolY(CompareAbleUtl.Is(CompareAbleUtl.More, afterModifiedTime, beforeModifiedTime));

		Hash_algo algo = Hash_algo_.New__md5();
		String loadHash = Hash_algo_utl.Calc_hash_as_str(algo, Io_mgr.Instance.LoadFilBry(load));
		String saveHash = Hash_algo_utl.Calc_hash_as_str(algo, Io_mgr.Instance.LoadFilBry(save));
		GfoTstr.EqObj(loadHash, saveHash);
	}
}
