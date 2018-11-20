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
            return context.Profiles.Find(id);
        }

        public Profile AddProfile(Profile newProfile)
        {
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
            properties.Add(updatedProfile.LeaderboardPosition);


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
                profile.FirstName = updatedProfile.FirstName;
                profile.LastName = updatedProfile.LastName;
                profile.NickName = updatedProfile.NickName;
                profile.Age = updatedProfile.Age;
                profile.Country = updatedProfile.Country;
                profile.ProfilePicture = updatedProfile.ProfilePicture;
                profile.LeaderboardPosition = updatedProfile.LeaderboardPosition;
                profile.CompletedPilgrimages = updatedProfile.CompletedPilgrimages;
                return true;
            }

        }

        public bool isEmpty(string input)
        {
            if (input == "")
                return true;
            return false;
        }

    }
}
