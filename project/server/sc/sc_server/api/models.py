from django.db import models

class image(models.Model):
    image_url = models.CharField(max_length=1000)