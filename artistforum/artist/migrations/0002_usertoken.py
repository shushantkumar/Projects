# Generated by Django 2.0.1 on 2018-03-15 13:31

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('artist', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='UserToken',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('yellowant_token', models.CharField(max_length=200)),
                ('yellowant_id', models.IntegerField(default=0)),
                ('yellowant_integration_id', models.IntegerField(default=0)),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='artist.Artist')),
            ],
        ),
    ]