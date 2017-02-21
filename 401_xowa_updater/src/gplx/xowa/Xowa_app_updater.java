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
package gplx.xowa;
import gplx.Io_url_;
import gplx.core.envs.*;
public class Xowa_app_updater {
	public static void main(String[] args) {
		Env_.Init_swt(args, Xowa_app_updater.class);
		if (args.length == 0) {
			System.out.println("could not run app_updater; must pass 2 arguments: manifest url and update_root");
			return;
		}
		Xoa_manifest_view.Run(Io_url_.new_fil_(args[0]), Io_url_.new_dir_(args[1]));
	}
}
