Introduction
------------

This library was created from the coordinates package of `JSky <https://jsky.sourceforge.net/>`_ version 3.0. The primary goal is to create a JavaScript coordinate conversion library for use in a browser. Secondary goals are to make the library available for Java applications and node.js applications. It is implemented in Java and is converted to JavaScript using `JSweet <https://www.jsweet.org/>`_.

Installation
------------

:code:`coordtxl` can be installed using :code:`npm`::

        bash$ npm install coordtxl


Usage
-----

The world coordinate system is specified using a `FITS <https://en.wikipedia.org/wiki/FITS>`_ header stored in a *keyword*/*value* map object. This is an example of such a map based on a test image from `CASA <https://casadocs.readthedocs.io/en/latest/>`_::

  { "ALTRPIX": 1.0, "ALTRVAL": 1474985003.2841744, "BITPIX": -32, "BSCALE": 1.0,
    "BTYPE": "Intensity", "BUNIT": "        ", "BZERO": 0.0,
    "CDELT": [-0.0033333333333333335, 0.0033333333333333335, 1.0, -121128418.21747687],
    "CHNCHNKS": 1, "COMMENT": "casacore non-standard usage: 4 LSD, 5 GEO, 6 SOU, 7 GAL",
    "CRPIX": [257.0, 257.0, 1.0, 1.0], "CRVAL": [299.86875000000003, 40.73375000000001, 1.0, -0.0],
    "CTYPE": ["RA---SIN", "DEC--SIN", "STOKES  ", "VOPT    "], "CUNIT1": "deg     ",
    "CUNIT2": "deg     ", "CUNIT3": "        ", "CUNIT4": "m/s     ", "DATE": "2023-06-27T16:23:16.059171",
    "DATE-OBS": "2008-11-23T18:58:00.275202", "DISTANCE": 0.0, "END": "", "EQUINOX": 2000.0,
    "EXTEND": true, "IMAGENME": "/tmp/iclean-demo/test.residual", "INSTRUME": "VLA     ",
    "LATPOLE": 40.73375000000001, "LONPOLE": 180.0, "MEMAVAIL": 12.914434432983398,
    "MEMREQ": 0.01373291015625, "MPIPROCS": 1, "NAXIS": [4, 512, 512, 1, 1], "OBJECT": "fake    ",
    "OBSDEC": 40.73375000000001, "OBSERVER": "CASA simulator", "OBSGEO-X": -1601185.3650000019,
    "OBSGEO-Y": -5041977.546999999, "OBSGEO-Z": 3554875.8700000006, "OBSRA": 299.86875, "ORIGIN":
    "casacore-@PROJECT_VERSION@", "PC1_1": 1.0, "PC1_2": 0.0, "PC1_3": 0.0, "PC1_4": 0.0, "PC2_1": 0.0,
    "PC2_2": 1.0, "PC2_3": 0.0, "PC2_4": 0.0, "PC3_1": 0.0, "PC3_2": 0.0, "PC3_3": 1.0, "PC3_4": 0.0,
    "PC4_1": 0.0, "PC4_2": 0.0, "PC4_3": 0.0, "PC4_4": 1.0, "PV2_1": 0.0, "PV2_2": 0.0,
    "RADESYS": "FK5     ", "RESTFRQ": 1474985003.2841744, "SIMPLE": true, "SPECSYS": "LSRK    ",
    "TELESCOP": "VLA     ", "TIMESYS": "UTC     ", "VELREF": 1 }

represented in `JSON format <https://en.wikipedia.org/wiki/JSON>`_ (referenced as :code:`'./test-residual-header.json'` below). The FITS map object is used to initialize :code:`MapKeywordProvider` which provides the values to the :code:`WCSTransform` object. This object provides functions for conversion from image to world coordinates. This is an example of its usage::

  import { Point2D,
           WorldCoords,
           WCSTransform,
           MapKeywordProvider } from 'coordtxl'

  import * as fs from 'fs'

  // load FITS header
  let rawdata = fs.readFileSync('./test-residual-header.json')
  let hdr = JSON.parse(rawdata.toString( ))
  // create coordtxl map coupler
  let kwp = new MapKeywordProvider(hdr)
  // create coordinate tranformation object
  let wcs = new WCSTransform(kwp)
  // check object state
  console.log( wcs.isWCS( ) )
  // create a point to be transformed
  let p = new Point2D(0.0,0.0)
  console.log(p)
  // convert point from image to world coordinates
  wcs.imageToWorldCoords(p,false)
  console.log(p)
  // create string representation of the point
  let wcstr = new WorldCoords(p.getX(),p.getY()).toString( )
  console.log( wcstr )
  // create X and Y string representation
  console.log( new WorldCoords(p.getX(),p.getY()).format(2000) );

FITS Keywords Used
------------------

This is a list of the FITS header keywords used from a :code:`WCSKeywordProvider` (*circa July, 2023*) by the :code:`WCSTransform` conversion class. Each line includes the member function called, the keyword retrieved and along with sample values from a test :code:`test.residual` interactive clean residual image. The :code:`<default: ...>` return values indicates the value actually returned and the fact that the default value was returned instead of a value supplied from the CASA FITS header.

#. :code:`getDoubleValue`:	NAXIS1, 4.0
#. :code:`getDoubleValue`:	NAXIS2, 512.0
#. :code:`getDoubleValue`:	EQUINOX, 2000.0
#. :code:`findKey`:	PLTRAH
#. :code:`getStringValue`:	CTYPE1, RA---SIN
#. :code:`getStringValue`:	CTYPE2, DEC--SIN
#. :code:`getDoubleValue`:	CRPIX1, 257.0
#. :code:`getDoubleValue`:	CRPIX2, 257.0
#. :code:`getDoubleValue`:	CRVAL1, 299.86875000000003
#. :code:`getDoubleValue`:	CRVAL2, 40.73375000000001
#. :code:`getDoubleValue`:	CDELT1, -0.0033333333333333335
#. :code:`getDoubleValue`:	CDELT2, 0.0033333333333333335
#. :code:`getDoubleValue`:	CROTA1, <default: 0.0>
#. :code:`getDoubleValue`:	CROTA2, <default: 0.0>
#. :code:`getDoubleValue`:	CCPIX1, <default: 0.0>
#. :code:`getDoubleValue`:	CCPIX2, <default: 0.0>
#. :code:`getDoubleValue`:	CCROT1, <default: 0.0>
#. :code:`findKey`:	EQUINOX
#. :code:`getDoubleValue`:	EQUINOX, 2000.0
#. :code:`getDoubleValue`:	DATE-OBS, <default: 0.0>
#. :code:`getDoubleValue`:	EPOCH, <default: 0.0>
#. :code:`findKey`:	RADECSYS

