from yellowant import YellowAnt
import json
from .models import Artist
from yellowant.messageformat import MessageClass, MessageAttachmentsClass, MessageButtonsClass, AttachmentFieldsClass

class CommandCenter(object):
	"""docstring for ClassName"""
	def __init__(self, yellowant_user_id,yellowant_integration_id,function_name,args):

		self.yellowant_user_id = yellowant_user_id
		self.yellowant_integration_id = yellowant_integration_id
		self.function_name = function_name
		self.args = args

	def parse(self):
		self.commands = {
			'getartists':self.getartists

		}
		return self.commands[self.function_name](self.args)

	def getartists(self,args):
		id = args['id']
		try:
			artist  = Artist.objects.get(artistName=id)
		except Artist.DoesNotExist:
			m = MessageClass()
			m.message_text = "Artist Not Found"
			return m.to_json

		m = MessageClass()
		m.message_text = "Here are artist details"

		artist_attachment = MessageAttachmentsClass()
		artist_attachment.title = artist.artistInterest

		field_age = AttachmentFieldsClass()
		field_age.title = "artistAge"
		field_age.value = artist.artistAge

		artist_attachment.attach_field(field_age)
		m.attach(customer_attachment)
		return m.to_json()



		