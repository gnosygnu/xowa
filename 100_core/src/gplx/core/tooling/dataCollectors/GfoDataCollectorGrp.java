/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.tooling.dataCollectors;
import java.util.LinkedHashMap;
import java.util.List;
public class GfoDataCollectorGrp {
	private final String key;
	private final LinkedHashMap<String, Object> hash = new LinkedHashMap<>();
	public GfoDataCollectorGrp(String key) {
		this.key = key;
	}
	public String Key() {return key;}
	public GfoDataCollectorGrp Add(String dataKey, String dataVal) {
		hash.put(dataKey, dataVal);
		return this;
	}
	public GfoDataCollectorGrp Add(String dataKey, List<?> dataVal) {
		hash.put(dataKey, dataVal);
		return this;
	}
	public Object Get(String dataKey) {
		return hash.get(dataKey);
	}
}
