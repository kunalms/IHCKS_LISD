import pyaudio
import sys
import wave
def wav_file_creator():
    chunk = 1024
    FORMAT = pyaudio.paInt16
    CHANNELS = 1
    RATE = 44100
    RECORD_SECONDS = 5

    p = pyaudio.PyAudio()

    stream = p.open(format=FORMAT,
                    channels=CHANNELS,
                    rate=RATE,
                    input=True,
                    output=True,
                    frames_per_buffer=chunk)

    allvalues=[]
    for i in range(0,int( 44100 / chunk * RECORD_SECONDS)):
        data = stream.read(chunk)
        allvalues.append(data)
    
    stream.stop_stream()
    stream.close()
    p.terminate()
    data = b''.join(allvalues)
    wf = wave.open('src/wav/test.wav', 'wb')
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    wf.writeframes(data)
    wf.close()
    print("FILE CREATED")
