/*
 * Copyright (C) 2018 Skywatch. https://www.skywatch.com
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */
package org.esa.snap.landcover.dataio.AAFC;

import org.esa.snap.core.dataop.resamp.Resampling;
import org.esa.snap.core.util.ResourceInstaller;
import org.esa.snap.engine_utilities.util.Settings;
import org.esa.snap.landcover.dataio.AbstractLandCoverModelDescriptor;
import org.esa.snap.landcover.dataio.LandCoverModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class AESBEOS2018CropModelDescriptor extends AbstractLandCoverModelDescriptor {

    public static final String NAME = "AAFC Canada 2018 Crop";

    private static final File INSTALL_DIR = new File(Settings.instance().getAuxDataFolder().getAbsolutePath(),
            "LandCover" + File.separator + "AAFC" + File.separator + "AESB_EOS_Crop_2018");
    private static final File[] fileList = new File[]{
            new File(INSTALL_DIR, "aci_2018_ab.zip"),
            new File(INSTALL_DIR, "aci_2018_bc.zip"),
            new File(INSTALL_DIR, "aci_2018_mb.zip"),
            new File(INSTALL_DIR, "aci_2018_nb.zip"),
            new File(INSTALL_DIR, "aci_2018_nl.zip"),
            new File(INSTALL_DIR, "aci_2018_ns.zip"),
            new File(INSTALL_DIR, "aci_2018_on.zip"),
            new File(INSTALL_DIR, "aci_2018_pe.zip"),
            new File(INSTALL_DIR, "aci_2018_qc.zip"),
            new File(INSTALL_DIR, "aci_2018_sk.zip")
    };

    public AESBEOS2018CropModelDescriptor() {
        remotePath = "http://step.esa.int/auxdata/landcover/AAFC/AESB_EOS_Crop_2018/";
        name = NAME;
        NO_DATA_VALUE = -9999d;
        installDir = INSTALL_DIR;
        final Path moduleBasePath = ResourceInstaller.findModuleCodeBasePath(this.getClass());
        colourIndexFile = moduleBasePath.resolve("org/esa/snap/landcover/auxdata/aafc/aafc_crop_index.col");
    }

    @Override
    public synchronized boolean installFiles() {
        installMetadata();

        if (installDir != null) {
            isInstalled = true;
        }
        return isInstalled;
    }

    @Override
    public LandCoverModel createLandCoverModel(final Resampling resampling) throws IOException {
        return new FileLandCoverProvincialModel(this, fileList, resampling);
    }

    public String createTileFilename(final int minLat, final int minLon) {
        return fileList[0].getName();
    }
}
