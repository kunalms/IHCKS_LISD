from gpsdata import *
import requests
import time
import json
from datetime import datetime

def trip_cont(start_time,flag,tripid):
	comp=False
	while not comp:
		try:
			location=loc()
			dt=datetime.now()
			cur_time=int(time.mktime(dt.timetuple()))
			if location:
				latitude=(location[0])
				longitude=(location[1])
				if latitude != "0.0" and longitude != "0.0":
					userdata={}
					userdata["trip_id"]=tripid
					userdata["user_id"]="109"
					userdata["vehicle_id"]="104"
					userdata["timestamp"]=dt
					userdata["latitude"]=latitude
					userdata["longitude"]=longitude
					userdata["is_trip_live"]=flag;
					link = """https://api.expertise97.hasura-app.io/api/update_trip?trip_id={str0}&user_id={str1}&vehicle_id={str2}&latitude={str3}&longitude={str4}&timestamp={str5}&is_trip_live={str6}""".format(str0=userdata["trip_id"],
																											str1=userdata["user_id"],
																											str2=userdata["vehicle_id"],
																											str3=userdata["latitude"],
																											str4=userdata["longitude"],
																											str5=userdata["timestamp"],
																											str6=userdata["is_trip_live"])
					resp = requests.get(link,timeout=10)
					comp=True
			if flag:
				return cur_time-start_time;
		except:
			pass
