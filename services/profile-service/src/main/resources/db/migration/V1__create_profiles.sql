CREATE SCHEMA IF NOT EXISTS profile;

CREATE TABLE IF NOT EXISTS profile.profiles (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,

    username VARCHAR(50) NOT NULL,

    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20),

    kyc_status VARCHAR(20) NOT NULL DEFAULT 'UNVERIFIED',

    preferences JSONB,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_profiles_username
    ON profile.profiles (username);

CREATE INDEX IF NOT EXISTS idx_profiles_email
    ON profile.profiles (email);

CREATE INDEX IF NOT EXISTS idx_profiles_phone
    ON profile.profiles (phone);
