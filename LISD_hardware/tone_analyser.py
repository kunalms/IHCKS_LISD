import requests
import json
from audiorecord import *

wav_file_creator()
tone_ids=["anger","disgust","fear","sadness"]
data = open('src/wav/test.wav', 'rb').read()
resp = requests.post(url='http://TONER.eu-de.mybluemix.net/speech',
                    data=data,
                    headers={'Content-Type': 'application/octet-stream'})
getdata=json.loads(resp.content.decode('utf-8'))
tones=getdata["tone_categories"]
vals=''
for val in tones:
    if(val["category_id"]=="emotion_tone"):
        for scores in val["tones"]:
            if (scores["tone_id"] in tone_ids):
                print(str(scores["tone_id"])+" "+str(scores["score"]))
                vals+=str(scores["tone_id"])+" "+str(scores["score"])
        break;
cltdata={}
cltdata["violation"]=vals
cltdata["number"]="109"
cltdata["action"]=1
cltdata["alertpriority"]=100
userdata=json.dumps(cltdata)
print(userdata)
headers = {'Content-type': 'application/json', 'Accept': 'text/plain'}
resp=requests.post(url="http://lidsmysqldb.clouadpp.net/sih2017/lids-api/sendAlert.php",data=userdata)
