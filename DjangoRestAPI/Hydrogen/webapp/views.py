from django.shortcuts import render

from django.http import  HttpResponse               #for httpresponse
from django.shortcuts import get_object_or_404      #for 404 error
from rest_framework.views import APIView            #so that normal views can return an API data
from rest_framework.response import Response        #main response
from rest_framework import status,generics,permissions                   #returns status
from . models import employees
from . serializers import employeesSerializer

#this class inherits from APIView
class employeeList(APIView):
    def get(self, request):
        employees1 = employees.objects.all()    #storing all objects in this variable

        #it will take all the object and convert it to json
        serializer = employeesSerializer(employees1, many=True)
        return Response(serializer.data)

    def post(self):
        pass
    def put(self):
        pass
    def delete(self):
        pass
