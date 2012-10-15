/*
 * Copyright (C) 2010 Brockmann Consult GmbH (info@brockmann-consult.de)
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

package org.esa.beam.framework.gpf.main;

import org.esa.beam.util.SystemUtils;

import java.util.Locale;

/**
 * The entry point for the GPF command-line tool (Graph Processing Tool, GPT).
 * For usage, see {@link org/esa/beam/framework/gpf/main/CommandLineUsage.txt}
 * or use the option "-h".
 *
 * @since BEAM 4.10 (renamed from {@code Main}).
 */
public class GPT {

    public static void main(String[] args) throws Exception {
        try {
            run(args);
        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void run(String[] args) throws Exception {
        if (System.getProperty("ceres.context") == null) {
            System.setProperty("ceres.context", "beam");
        }
        Locale.setDefault(Locale.ENGLISH); // Force usage of english locale
        SystemUtils.init3rdPartyLibs(GPT.class.getClassLoader());
        final CommandLineTool commandLineTool = new CommandLineTool();
        commandLineTool.run(args);
    }

}
