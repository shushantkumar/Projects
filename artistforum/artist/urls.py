from django.conf.urls import url
from . import views

app_name = 'artist'
urlpatterns = [

    # /music/
    url(r'^$', views.IndexView.as_view(), name='index'),
    url(r'^work/', views.WorkView.as_view(), name='work')

    ]