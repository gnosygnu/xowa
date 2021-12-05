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
package gplx.objects.events;
import java.util.ArrayList;
import java.util.List;
public class GfoEvent<A> {
	private final List<GfoHandler<A>> handlers = new ArrayList<>();
	private final String name;
	private final GfoEventOwner owner;
	public GfoEvent(String name, GfoEventOwner owner) {
		this.name = name;
		this.owner = owner;
	}
	public void Add(GfoHandler<A> handler) {
		owner.EventsEnabledSet(true);
		handlers.add(handler);
	}
	public void Run(A args) {
		for (GfoHandler<A> handler : handlers) {
			handler.Run(owner, args);
		}
	}
}
