import matplotlib.pyplot as plt
import numpy as np
from matplotlib.animation import FuncAnimation
import psutil
import collections
import os
from datetime import datetime



def read_speed():
    output = os.popen('snmpwalk -v2c -c ornekparola 127.0.0.1 1.3.6.1.2.1.2.2.1.5')
    sum=0
    for line in output.readlines():
        #print(line, end='')
        x=line.split()
        numn=x[3]
        #print(numn)
        sum+=int(numn)
    #print(sum)
    return sum

def read_out():
    output = os.popen('snmpwalk -v2c -c ornekparola 127.0.0.1 1.3.6.1.2.1.2.2.1.16')
    sum=0
    speed=read_speed()
    for line in output.readlines():
        #print(line, end='')
        x=line.split()
        numn=x[3]
        #print(numn)
        sum+=int(numn)
    #print(sum)
    outSnmp=(sum*8*100)/(speed)
    return outSnmp

def read_in():
    output = os.popen('snmpwalk -v2c -c ornekparola 127.0.0.1 1.3.6.1.2.1.2.2.1.10')
    sum=0
    speed=read_speed()
    for line in output.readlines():
        #print(line, end='')
        x=line.split()
        numn=x[3]
        #print(numn)
        sum+=int(numn)
    #print(sum)
    inSnmp=(sum*8*100)/(speed)
    return inSnmp

# function to update the data
def my_function(i):
    now = datetime.now()

    # get data
    #cpu in
    #ram out
    cpu.popleft()
    cpu.append(read_in()/1000)
    ram.popleft()
    ram.append(read_out()/1000)
    time.popleft()
    time.append(now.strftime("%H:%M:%S"))
    # clear axis
    ax.cla()
    ax1.cla()
    # plot cpu
    #ax.plot(cpu, time,label = "Gelen", linestyle="-", marker='o')
    ax.plot(cpu)
    ax.scatter(len(cpu)-1, cpu[-1])
    ax.text(len(cpu)-1, cpu[-1]+2, "{}%".format(cpu[-1]))
    ax.set_ylim(200,250)
    ax.set_title('Gelen Trafik / Zaman')
    ax.set_ylabel('Gelen Trafik')
    ax.set_xlabel('Zaman')

    # plot memory
    ax1.plot(ram)
    ax1.scatter(len(ram)-1, ram[-1])
    ax1.text(len(ram)-1, ram[-1]+2, "{}%".format(ram[-1]))
    ax1.set_ylim(0,100)
    ax1.set_title('Giden Trafik / Zaman')
    ax1.set_ylabel('Gelen Trafik')
    ax1.set_xlabel('Zaman')
# start collections with zeros
cpu = collections.deque(np.zeros(10))
ram = collections.deque(np.zeros(10))
time= collections.deque(np.zeros(10))
# define and adjust figure

fig = plt.figure(figsize=(12,6), facecolor='#DEDEDE')
ax = plt.subplot(121)
ax1 = plt.subplot(122)
ax.set_facecolor('#DEDEDE')
ax1.set_facecolor('#DEDEDE')

# animate
ani = FuncAnimation(fig, my_function, interval=1000)

plt.show()