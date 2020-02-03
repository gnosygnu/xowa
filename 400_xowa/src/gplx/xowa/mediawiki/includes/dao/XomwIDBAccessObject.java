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
/**
* Interface for database access objects.
*
* Classes using this support a set of constants in a bitfield argument to their data loading
* functions. In general, objects should assume READ_NORMAL if no flags are explicitly given,
* though certain objects may assume READ_LATEST for common use case or legacy reasons.
*
* There are four types of reads:
*   - READ_NORMAL    : Potentially cached read of data (e.g. from a replica DB or stale replica)
*   - READ_LATEST    : Up-to-date read as of transaction start (e.g. from master or a quorum read)
*   - READ_LOCKING   : Up-to-date read as of now, that locks (shared) the records
*   - READ_EXCLUSIVE : Up-to-date read as of now, that locks (exclusive) the records
* All record locks persist for the duration of the transaction.
*
* A special constant READ_LATEST_IMMUTABLE can be used for fetching append-only data. Such
* data is either (a) on a replica DB and up-to-date or (b) not yet there, but on the master/quorum.
* Because the data is append-only, it can never be stale on a replica DB if present.
*
* Callers should use READ_NORMAL (or pass in no flags) unless the read determines a write.
* In theory, such cases may require READ_LOCKING, though to avoid contention, READ_LATEST is
* often good enough. If UPDATE race condition checks are required on a row and expensive code
* must run after the row is fetched to determine the UPDATE, it may help to do something like:
*   - a) Start transaction
*   - b) Read the current row with READ_LATEST
*   - c) Determine the new row (expensive, so we don't want to hold locks now)
*   - d) Re-read the current row with READ_LOCKING; if it changed then bail out
*   - e) otherwise, do the updates
*   - f) Commit transaction
*
* @since 1.20
*/
public interface XomwIDBAccessObject {
}
