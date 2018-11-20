using System;
using System.Collections.Generic;
using System.Text;
using static System.Net.Mime.MediaTypeNames;

namespace DataLinkLayer.Models
{
    public class Profile
    {
        public int ID { get; set; }

        public string FirstName { get; set; }

        public string LastName { get; set; }

        public string NickName { get; set; }

        public int Age { get; set; }

        public string Country { get; set; }




        public int LeaderboardPosition { get; set; }

        public byte[] ProfilePicture { get; set; }

        public DateTime DateCreated { get; set; }

        public Pilgrimage[] CompletedPilgrimages { get; set; }


    }
}
