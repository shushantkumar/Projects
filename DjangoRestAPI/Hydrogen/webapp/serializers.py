from rest_framework import serializers
from . models import employees

#here we are converting into json response
#add Serializer at end of class name
class employeesSerializer(serializers.ModelSerializer):

    class Meta:
        model = employees
        #This below can be used for separate or as after that
        #fields = ('firstname','lastname')
        fields = '__all__'