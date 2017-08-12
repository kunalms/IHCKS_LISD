from gpsdata import *
import requests
import time
import json
from datetime import datetime

def trip_init():
	comp=False
	trip_id=0
	while not comp:
		location=loc()
		dt=datetime.now()
		cur_time=int(time.mktime(dt.timetuple()))
		latitude=(location[0])
		longitude=(location[1])
		if latitude != "0.0" and longitude != "0.0":
			userdata={}
			userdata["user_id"]="109"
			userdata["vehicle_id"]="104"
			userdata["timestamp"]=cur_time
			userdata["latitude"]=latitude
			userdata["longitude"]=longitude
			headers = {'Accept': 'text/plain'}
			link = 'https://api.expertise97.hasura-app.io/api/insert_trip?user_id='+userdata["user_id"]+
																	"&vehicle_id="+userdata["vehicle_id"]+
																	"&latitude="+userdata["latitude"]+
																	"&longitude="+userdata["longitude"]+
																	"&timestamp="+userdata["timestamp"]
			resp = requests.get(link,headers=headers,timeout=10)
			if resp.status_code==200:
				print(resp.content)
				getdata=json.loads(resp.content.decode('utf-8'))
				trip_id=getdata["trip_id"]
		comp=True
	return cur_time,trip_id

