# -*- coding: utf-8 -*-
"""
Created on Thu Feb 25 21:43:19 2016

@author: smusali
"""

import json

FILENAME = "Courses.json";
with open(FILENAME) as data_file:    
    data = json.load(data_file)

APPLICATION_ID = "H0NpxZR0eGLP4kgYFvHujDybG43HwI0ktVeWjN8u";
REST_API_KEY   = "ilhlGks7fj6jppBbW5OYz42jl3RN8Z4EUebQHGZK";

from parse_rest.connection import register
register(APPLICATION_ID, REST_API_KEY);
from parse_rest.datatypes import Object
myClassName = "Course";
Course = Object.factory(myClassName);
subjects = data.keys();
for sub in subjects:
    numbers = data[sub]
    nums = numbers.keys();
    for num in nums:
        title = numbers[num];
        course = Course(subject=sub, number=num, title=title);
        course.save();
        print sub+" "+num+": "+title;