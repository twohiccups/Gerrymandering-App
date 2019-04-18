import json
from enum import Enum

class State(Enum) {
	OHIO = 1
	MASSACHUSETTS = 2
	OREGON = 3
}

def addPrecincts(fileName):
    precincts = {};
    with open(fileName) as f:
        data = json.load(f)
    length = len(data['features'])
    for i in range(0, length):
        properties = data['features'][i]['properties']
        p = {
            'ID': properties['ID'],
            'area': properties['area'],
            'population': properties['population']
        }
        precincts[i] = p
    return precincts

def createState(stateName) {
	state = {}
	state['name'] = stateName;
	state['ID'] = State[stateName];
	state['numOfPrecincts'] = len(['unassigned']['precincts'])
	state['unassigned'] = {}
	fileName = stateName + 'Final.json'
	state['unassigned']['precincts']  = addPrecincts(fileName)
	outfileName = stateName + 'Serialized.json'
	with open(outfileName, 'w') as outfile:
        json.dump(state, outfile)
}