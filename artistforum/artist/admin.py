from django.contrib import admin

# Register your models here.
from .models import Artist, FieldofWork

admin.site.register(Artist)
admin.site.register(FieldofWork)
