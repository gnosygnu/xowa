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
package gplx.gfui.imgs; import gplx.*; import gplx.gfui.*;
import org.junit.*;
import gplx.core.consoles.*;
import gplx.core.ios.*;
import gplx.core.security.*;
import gplx.gfui.imgs.*;
public class ImageAdp_tst {
	@Before public void setup() {
		load = Tfds.RscDir.GenSubFil_nest("150_gfui", "imgs", "strawberry_java.bmp");	
	}	ImageAdp img; Io_url load;
	@Test  public void load_() {
		img = ImageAdp_.file_(load);
		Tfds.Eq(80, img.Width());
		Tfds.Eq(80, img.Height());
		Tfds.Eq("80,80", img.Size().toString());
		Tfds.Eq(img.Url(), load);
	}
	@Test  public void SaveAsBmp() {
		img = ImageAdp_.file_(load);
		Io_url save = load.GenNewNameOnly("strawberry_temp");
		DateAdp beforeModifiedTime = Io_mgr.Instance.QueryFil(save).ModifiedTime();
		img.SaveAsBmp(save);
		DateAdp afterModifiedTime = Io_mgr.Instance.QueryFil(save).ModifiedTime();
		Tfds.Eq_true(CompareAble_.Is(CompareAble_.More, afterModifiedTime, beforeModifiedTime));

		Hash_algo algo = Hash_algo_.New__md5();
		String loadHash = algo.Hash_stream_as_str(Console_adp_.Noop, Io_mgr.Instance.OpenStreamRead(load));
		String saveHash = algo.Hash_stream_as_str(Console_adp_.Noop, Io_mgr.Instance.OpenStreamRead(save));
		Tfds.Eq(loadHash, saveHash);
	}
}
