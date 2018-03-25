from django.shortcuts import render
from django.http import HttpResponseRedirect,HttpResponse
from django.views import generic
from .models import Artist, FieldofWork, UserToken
from django.views.generic import View
from yellowant import YellowAnt
from artistforum import settings
from .commandcenter import CommandCenter

# Create your views here.


class IndexView(generic.ListView):
    template_name = 'artist/index.html'

    def get_queryset(self):
        return Artist.objects.all()

class WorkView(generic.ListView):
    template_name = 'artist/work.html'

    def get_queryset(self):
        return FieldofWork.objects.all()

def redirectToAuth(request):
	print(request)
	return HttpResponseRedirect("https://www.yellowant.com/api/oauth2/authorize/?client_id=%s&response_type=code&redirect_url=%s"%(settings.YELLOWANT_CLIENT_ID, settings.YELLOWANT_REDIRECT_URL))

def yellowantRedirectUrl(request):
	code = request.GET.get("code",False)
	# code = "64HDykUcK2SQPJd0VLansK9tLHvEvJ"
	if code is False:
		return HttpResponse("Invalid Response")
	else:
		y = YellowAnt(app_key=settings.YELLOWANT_CLIENT_ID, app_secret=settings.YELLOWANT_CLIENT_SECRET,
                  access_token=None,
                  redirect_uri=settings.YELLOWANT_REDIRECT_URL)
		print(y)
		access_token_dict = y.get_access_token(code)
		print(access_token_dict)
		access_token = access_token_dict['access_token']
		print(access_token)

		user_yellowant_object = YellowAnt(access_token=access_token)
		profile = user_yellowant_object.get_user_profile()
		yellowant_user = YellowAnt(access_token=access_token)
		user_integration = yellowant_user.create_user_integration()
		# print(user)
		print(user_integration)

		ut = UserToken.objects.create(yellowant_token=access_token,yellowant_id=profile['id'],yellowant_integration_id = user_integration['user_application'])
		return HttpResponse("User Authenticated")

def api_url(request):    
	data = json.loads(request.POST["data"])
	args = data["args"]
	service_application = data["application"]
	verification_token = data['verification_token']
	function_id = data['function']
	function_name = data['function_name']

	if verification_token == settings.YELLOWANT_VERIFICATION_TOKEN:
	# Processing command in some class Command and sending a Message Object
	  message = CommandCenter(data['user'], data['application'], data['function_name'], data['args']).parse()
	    # Returning message response
	  return HttpResponse(message)
	else:
	  # Handling incorrect verification token
	  error_message = {"message_text":"Incorrect Verification token"}
	  return HttpResponse(json.dumps(error_message), content_type="application/json")

