Introduction
------------

This library was created from the coordinates package of `JSky <https://jsky.sourceforge.net/>`_. The primary goal is to create a JavaScript coordinate conversion library for use in a browser. Secondary goals are to make the library available for Java applications and node.js applications. It is implemented in Java and is converted to JavaScript using `JSweet <https://www.jsweet.org/>`_.

Setup
-----

You can setup this project by running :code:`gradle wrapper`.

Running Tests
-------------

The Java tests can be run like :code:`./gradlew test --tests 'coordtxl.*'`.

Build Java Library
------------------

You can build the Java library (:code:`coordtxl-core.jar`) by running :code:`gradle jar`.

FITS Header Keywords Used
-------------------------

This is a list of the FITS header keywords used from a :code:`WCSKeywordProvider` (*circa July, 2023*). Each line includes the member function called, the keyword retrieved and along with sample values from a test :code:`test.residual` interactive clean residual image.

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
