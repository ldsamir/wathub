# -*- coding: utf-8 -*-
"""
Created on Thu Feb 18 00:55:11 2016

@author: smusali
"""

from bs4 import BeautifulSoup
import urllib2, json

def get_soup(url, header):
    return BeautifulSoup(urllib2.urlopen(urllib2.Request(url, headers=header)));

header = {'User-Agent': 'Mozilla/5.0'} 
CoursesDB = dict();

base_link = "https://github.com";
main_path = "/uWaterloo/Datasets/tree/master/Courses";
main__url = base_link + main_path;
main_soup = get_soup(main__url, header);
main_cont = main_soup.find_all("table");
main_body = main_cont[0].find_all("tbody");
main_data = main_body[0].find_all("td", {"class": "content"})[1:];
for content in main_data:
    content_info = content.find_all("a")[0];
    content_path = content_info["href"];
    content__url = base_link + content_path;
    content_name = content_path[content_path.find("Courses")+8:content_path.find(".csv")];
    if (content_name == "ESL"):
        content_name = "EMLS";
    print "Course Name: " + content_name;
    content_data = dict();
    content_soup = get_soup(content__url, header);
    content_cont = content_soup.find_all("tr");
    content_rnum = 0;
    for table_row in content_cont:
        if (content_rnum != 0):
            table_data = table_row.find_all("td")[1:];
            content_data[table_data[1].decode_contents()] = table_data[2].decode_contents();
        content_rnum += 1;
    print " # of Courses: " + str(content_rnum);
    CoursesDB[content_name] = content_data;
    
with open('Courses.json', 'w') as JSONFile:
    json.dump(CoursesDB, JSONFile);