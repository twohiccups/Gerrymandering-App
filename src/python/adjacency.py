import json
from shapely.geometry import geo

def areNeighbors(geom1, geom2):
    geom1 = geo.shape(geom1)
    geom2 = geo.shape(geom2)
    if geom1.intersects(geom2):
        return True
    return False

def createAdjacencyList(data):
    length = len(data['features'])
    adjacency = {}
    for i in range(0, length):
        adjacency[i] = []
    for i in range(0, length):
        for j in range(i+1, length):
            geom1 = data['features'][i]['geometry']
            geom2 = data['features'][j]['geometry']
            if areNeighbors(geom1,geom2):
                adjacency[i].append(j)
                adjacency[j].append(i)
                continue
    return adjacency

def saveAdjacencyList(adjacency, filename):
    with open(filename, 'w') as outfile:
       json.dump(adjacency, outfile)

def createAdjacencyListFromGeoJSON(fileIn, fileOut):
    with open(fileIn) as f:
        data = json.load(f)
    adjacency = createAdjacencyList(data)
    saveAdjacencyList(adjacency, fileOut)