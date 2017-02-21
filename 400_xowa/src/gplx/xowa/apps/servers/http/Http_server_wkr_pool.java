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
package gplx.xowa.apps.servers.http; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*; import gplx.xowa.apps.servers.*;
import gplx.core.primitives.*;
public class Http_server_wkr_pool {
	private final    Ordered_hash hash = Ordered_hash_.New(); private final    Int_obj_ref hash_key = Int_obj_ref.New_neg1();
	public boolean Enabled() {return max != 0;}
	public int Max() {return max;} private int max;
	public int Timeout() {return timeout;} private int timeout;
	public int Len() {return len;} private int len;
	public boolean Full() {return len >= max;}
	public void Init(int max, int timeout) {this.max = max; this.timeout = timeout;}
	public void Add(int uid) {
		if (max == 0) return;	// disabled
		synchronized (hash) {
			Int_obj_ref wkr_key = Int_obj_ref.New(uid);
			hash.Add(wkr_key, wkr_key);
			++len;
		}
	}
	public void Del(int uid) {
		if (max == 0) return;	// disabled
		synchronized (hash) {
			hash.Del(hash_key.Val_(uid));
			--len;
		}
	}
}
