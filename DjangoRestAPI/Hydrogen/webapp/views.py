from django.shortcuts import render

from django.http import  HttpResponse               #for httpresponse
from django.shortcuts import get_object_or_404      #for 404 error
from rest_framework.views import APIView            #so that normal views can return an API data
from rest_framework.response import Response        #main response
from rest_framework import status,generics,permissions                   #returns status
from . models import employees
from . serializers import employeesSerializer

from sklearn import tree
from sklearn import neural_network

#this class inherits from APIView
class employeeList(APIView):
    def get(self, request):
        # employees1 = employees.objects.all()    #storing all objects in this variable

        # #it will take all the object and convert it to json
        # serializer = employeesSerializer(employees1, many=True)
        # #n_classes = 10
        # #x = tf.placeholder('float', [None, 784])
        # #x = tf.placeholder('float')  this will also work only it will take any format not as 1 * 784

        # #Training data
        
        # #return Response(train_neural_network(x))
        clf = tree.DecisionTreeClassifier()
        clf1= neural_network.MLPClassifier()


        # # [height, weight, some_size]
        X = [[181, 80, 44], [177, 70, 43], [160, 60, 38], [154, 54, 37], [166, 65, 40],
             [190, 90, 47], [175, 64, 39],
             [177, 70, 40], [159, 55, 37], [171, 75, 42], [181, 85, 43]]

        Y = ['male', 'male', 'female', 'female', 'male', 'male', 'female', 'female',
             'female', 'male', 'male']


        # # Train our data
        clf = clf.fit(X, Y)
        clf1 = clf1.fit(X,Y)

        prediction = clf.predict([[190, 70, 35]])
        prediction1 = clf.predict([[190, 70, 35]])

        #print(prediction)
        #print(prediction1)
        return Response(prediction)

    def post(self):
        pass
    def put(self):
        pass
    def delete(self):
        pass
