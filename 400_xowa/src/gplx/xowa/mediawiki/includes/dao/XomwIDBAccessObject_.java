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
package gplx.xowa.mediawiki.includes.dao; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class XomwIDBAccessObject_ {
	/** Constants for Object loading bitfield flags (higher => higher QoS) */
	/** @var int Read from a replica DB/non-quorum */
	public static final int READ_NORMAL = 0;

	/** @var int Read from the master/quorum */
	public static final int READ_LATEST = 1;

	/* @var int Read from the master/quorum and synchronized out other writers */
	public static final int READ_LOCKING = READ_LATEST | 2; // READ_LATEST (1) and "LOCK IN SHARE MODE" (2)

	/** @var int Read from the master/quorum and synchronized out other writers and locking readers */
	public static final int READ_EXCLUSIVE = READ_LOCKING | 4; // READ_LOCKING (3) and "FOR UPDATE" (4)

	/** @var int Read from a replica DB or without a quorum, using the master/quorum on miss */
	public static final int READ_LATEST_IMMUTABLE = 8;

	// Convenience constant for tracking how data was loaded (higher => higher QoS)
	public static final int READ_NONE = -1; // not loaded yet (or the Object was cleared)
}
