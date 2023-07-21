/*************************************************
*** simple coordinate conversion in TypeScript ***
*************************************************/
import { Point2D } from './coordtxl/geom/Point2D.js'
import { MapKeywordProvider } from './coordtxl/coords/MapKeywordProvider.js'
import { WCSTransform } from './coordtxl/coords/WCSTransform.js'
import { WorldCoords } from './coordtxl/coords/WorldCoords.js'

import * as fs from 'fs'
import { strict as assert } from 'assert'

console.log(new Point2D( ))
let rawdata = fs.readFileSync('./test-residual-header.json')
let hdr = JSON.parse(rawdata.toString( ))
let kwp = new MapKeywordProvider(hdr)
let wcs = new WCSTransform(kwp)
console.log( wcs.isWCS( ) )
let p = new Point2D(0.0,0.0)
console.log(p)
wcs.imageToWorldCoords(p,false)
console.log(p)
let wcstr = new WorldCoords(p.getX(),p.getY()).toString( )
console.log( wcstr )
assert( wcstr == "20:02:54.100, +39:52:37.50 J2000", "conversion failed" )
