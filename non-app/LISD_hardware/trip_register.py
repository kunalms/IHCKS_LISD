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
			link = """https://api.expertise97.hasura-app.io/api/insert_trip?user_id={str1}&
																		vehicle_id={str2}&
																		latitude={str3}&
																		longitude={str4}&
																		timestamp={str5}""".format(str1=userdata["user_id"],
																									str2=userdata["vehicle_id"],
																									str3=userdata["latitude"],
																									str4=userdata["longitude"],
																									str5=userdata["timestamp"])
			print(link)
			resp = requests.get(link,timeout=10)
			if resp.status_code==200:
				print(resp.content)
				getdata=json.loads(resp.content.decode('utf-8'))
				trip_id=getdata["trip_id"]
		comp=True
	return cur_time,trip_id

