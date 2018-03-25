from django.db import models

class Artist(models.Model):
	artistName = models.CharField(max_length=200)
	artistAge = models.IntegerField(default=0)
	artistNationality = models.CharField(max_length=200)
	artistInterest = models.CharField(max_length=200)

	def __str__(self):
		return self.artistName




        

class FieldofWork(models.Model):
	artist = models.ForeignKey(Artist,on_delete=models.CASCADE)
	field = models.CharField(max_length=200)
	experience = models.CharField(max_length=200)
	works = models.CharField(max_length=200)

	def __str__(self):
		return self.works

class UserToken(models.Model):
	# user = models.ForeignKey(Artist,on_delete=models.CASCADE)
	yellowant_token = models.CharField(max_length=200)
	yellowant_id = models.IntegerField(default=0)
	yellowant_integration_id = models.IntegerField(default=0)
