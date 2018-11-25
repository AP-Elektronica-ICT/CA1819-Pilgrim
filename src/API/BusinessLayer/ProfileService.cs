using DataLinkLayer;
using DataLinkLayer.Models;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace BusinessLayer
{
    public class ProfileService
    {
        private readonly Context context;

        public ProfileService(Context context)
        {
            this.context = context;
        }

        public List<Profile> GetProfiles()
        {
            return context.Profiles.ToList();
        }

        public Profile GetProfile(int id)
        {
            Profile profile = context.Profiles.Find(id);
            if (profile != null)
            {
                profile.base64 = Convert.ToBase64String(profile.ProfilePicture);
                return profile;
            }
            return null;
        }

        public Profile GetProfileFireBaseID(int id)
        {
            Profile profile = context.Profiles.FirstOrDefault(m => m.fireBaseID == id);
            if (profile != null)
            {
                profile.base64 = Convert.ToBase64String(profile.ProfilePicture);
                return profile;
            }
            return null;

        }



        public Profile AddProfile(Profile newProfile)
        {
            ArrayList properties = new ArrayList();
            properties.Add(newProfile.FirstName);
            properties.Add(newProfile.LastName);
            properties.Add(newProfile.NickName);
            properties.Add(newProfile.Age);
            properties.Add(newProfile.fireBaseID);
            properties.Add(newProfile.Country);
            properties.Add(newProfile.CompletedPilgrimages);

            foreach (var item in properties)
            {
                if (item is string)
                {
                    System.Diagnostics.Debug.WriteLine("Item: " + item);
                    if (this.isEmpty(item.ToString()))
                        return null;
                }
                if (item is int?)
                {
                    if (isNull((int?)item))
                        return null;
                }
            }

            if (!fireBaseUserIDAlreadyUsed(newProfile.fireBaseID))
            {
                if (!UserNameAlreadyTaken(newProfile.NickName))
                {
                    newProfile.ProfilePicture = Convert.FromBase64String(newProfile.base64);
                    newProfile.base64 = "";
                    context.Profiles.Add(newProfile);
                    context.SaveChanges();
                    return newProfile;
                }
                return null;
            }
            return null;
        }

        public bool UpdateProfile(Profile updatedProfile)
        {
            ArrayList properties = new ArrayList();
            properties.Add(updatedProfile.FirstName);
            properties.Add(updatedProfile.LastName);
            properties.Add(updatedProfile.NickName);
            properties.Add(updatedProfile.Age);
            properties.Add(updatedProfile.Country);
            properties.Add(updatedProfile.ProfilePicture);
            properties.Add(updatedProfile.CompletedPilgrimages);


            foreach (var item in properties)
            {

                if (item is string)
                {
                    System.Diagnostics.Debug.WriteLine("Item: " + item);
                    if (this.isEmpty(item.ToString()))
                        return false;

                }

            }

            var profile = context.Profiles.Find(updatedProfile.ID);
            if (profile == null)
                return false;
            else
            {
                if (!UserNameAlreadyTaken(updatedProfile.NickName))
                {
                    profile.FirstName = updatedProfile.FirstName;
                    profile.LastName = updatedProfile.LastName;
                    profile.NickName = updatedProfile.NickName;
                    profile.Age = updatedProfile.Age;
                    profile.Country = updatedProfile.Country;
                    profile.ProfilePicture = updatedProfile.ProfilePicture;
                    profile.CompletedPilgrimages = updatedProfile.CompletedPilgrimages;
                    return true;
                }
                return false;
            }

        }

        public bool changeNickName(string input, int firebaseID)
        {
            Profile profile = context.Profiles.FirstOrDefault(p => p.fireBaseID == firebaseID);
            if (profile != null)
            {
                if(!isEmpty(input)  && !UserNameAlreadyTaken(input))
                {
                    profile.NickName = input;
                    context.SaveChanges();
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        public bool isEmpty(string input)
        {
            if (input == "")
                return true;
            return false;
        }

        public bool isNull(int? input)
        {
            if (input == null)
                return true;
            return false;
        }

        public bool UserNameAlreadyTaken(string input)
        {
            var profiles = context.Profiles;
            foreach (Profile prof in profiles)
            {
                if (prof.NickName.ToLower() == input.ToLower())
                    return true;
            }
            return false;
        }

        public bool fireBaseUserIDAlreadyUsed(int input)
        {
            var profiles = context.Profiles;
            foreach (Profile prof in profiles)
            {
                if (prof.fireBaseID == input)
                    return true;
            }
            return false;
        }

    }
}
