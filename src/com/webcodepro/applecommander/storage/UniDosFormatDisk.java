/*
 * AppleCommander - An Apple ][ image utility.
 * Copyright (C) 2002 by Robert Greene
 * robgreene at users.sourceforge.net
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation; either version 2 of the License, or (at your 
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.webcodepro.applecommander.storage;

import com.webcodepro.applecommander.storage.physical.ImageOrder;

/**
 * Manages a disk that is in UniDOS format.
 * This is basically DOS 3.3 except that the disk has two volumes of
 * each 400K.
 * <br>
 * Created on Dec 13, 2002.
 * @author Rob
 */
public class UniDosFormatDisk extends DosFormatDisk {
	/**
	 * Use this indicator to work with logical disk #1.
	 * It is essentially the offset into the disk image.
	 */
	public static final int UNIDOS_DISK_1 = 0;
	/**
	 * Use this indicator to work with logical disk #2.
	 * It is essentially the offset into the disk image.
	 */
	public static final int UNIDOS_DISK_2 = 409600;
	/**
	 * Indicates which logical disk to work with (by offset
	 * into the disk image itself).
	 */
	private int logicalOffset;
	/**
	 * Constructor for UniDosFormatDisk.
	 * @param filename
	 * @param diskImage
	 */
	public UniDosFormatDisk(String filename, ImageOrder imageOrder, int logicalOffset) {
		super(filename, imageOrder);
		this.logicalOffset = logicalOffset;
	}
	/**
	 * Create a UniDosFormatDisk.
	 */
	public static DosFormatDisk[] create(String filename, ImageOrder imageOrder) {
		UniDosFormatDisk disk1 = new UniDosFormatDisk(filename, imageOrder, UNIDOS_DISK_1);
		UniDosFormatDisk disk2 = new UniDosFormatDisk(filename, imageOrder, UNIDOS_DISK_2);
		disk1.format();
		disk2.format();
		return new UniDosFormatDisk[] { disk1, disk2 };
	}
	/**
	 * Answer with the name of this disk.
	 * @see com.webcodepro.applecommander.storage.FormattedDisk#getDiskName()
	 */
	public String getDiskName() {
		if (logicalOffset == UNIDOS_DISK_1) {
			return super.getDiskName() + " (Disk 1)";
		} else if (logicalOffset == UNIDOS_DISK_2) {
			return super.getDiskName() + " (Disk 2)";
		} else {
			return super.getDiskName();
		}
	}

	/**
	 * Returns the logical disk number.  This can be used to identify
	 * between disks when a format supports multiple logical volumes.
	 */
	public int getLogicalDiskNumber() {
		if (logicalOffset == UNIDOS_DISK_1) {
			return 1;
		} else if (logicalOffset == UNIDOS_DISK_2) {
			return 2;
		} else {
			return 0;
		}
	}
	/**
	 * Format the disk as UniDOS.
	 * @see com.webcodepro.applecommander.storage.FormattedDisk#format()
	 */
	public void format() {
		getImageOrder().format();
		format(31, 50, 32);
	}
}
