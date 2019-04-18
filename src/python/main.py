import json
from area import area
from shapely.geometry import geo
from adjacency import createAdjacencyListFromGeoJSON
import updatingGeoJson

createAdjacencyListFromGeoJSON('Ohio.json',
                                'OhioAdjacency.json');
createAdjacencyListFromGeoJSON('Oregon.json',
                                'OregonAdjacency.json');
createAdjacencyListFromGeoJSON('Massachusetts.json',
                                'MassachusettsAdjacency.json');




updatingGeoJson.createState('Ohio');
updatingGeoJson.createState('Massachusetts');
updatingGeoJson.createState('Oregon');