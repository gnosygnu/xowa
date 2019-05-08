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
package gplx.xowa.parsers.logs.stats; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*;
public class Xop_log_time_count {
	private int count;
	private long time;
	private int depth;
	private int depth_max;
	private long time_bgn;
	public int Count() {return count;}
	public long Time() {return time;}
	public int Depth_max() {return depth_max;}
	public void Bgn() {
		if (time_bgn == 0) {
			time_bgn = gplx.core.envs.System_.Ticks();
		}
		depth++;
		if (depth_max < depth)
			depth_max = depth;
	}
	public void End() {
		depth--;
		if (depth == 0) {
			long time_end = gplx.core.envs.System_.Ticks();
			this.time += time_end - time_bgn;
			this.time_bgn = 0;
		}
		this.count++;
	}
	public void Clear() {
		count = 0;
		time = 0;
		time_bgn = 0;
		depth = 0;
		depth_max = 0;
	}
}
