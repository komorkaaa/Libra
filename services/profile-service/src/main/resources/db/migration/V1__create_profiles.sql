CREATE TABLE IF NOT EXISTS profile.profiles (
id UUID PRIMARY KEY,
user_id UUID UNIQUE NOT NULL,
username VARCHAR(50) NOT NULL,
avatar_url VARCHAR(255),
phone VARCHAR(20),
preferences JSONB,
created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_profiles_username ON profile.profiles (username);